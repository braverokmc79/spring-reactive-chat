<template>
    <div>
        <hr/>
        <b-button @click="showModal">
            새 채팅
        </b-button>
        <hr/>
        <b-modal ref="newChatModal" hide-footer size="lg">
            <b-form @submit="submit" @keydown.enter.prevent="searchUsers">
                <div v-if="selected.length > 1 && edit === false">
                    <b-form-input v-model="groupChatName"
                                  type="text"
                                  placeholder="그룹 채팅 이름을 입력하세요."
                                  required
                    ></b-form-input>
                    <hr/>
                </div>
                <b-container class="container">
                    <b-row>
                        <b-col cols="8">
                            <b-form-input v-model="searchString"
                                          type="text"
                                          placeholder="사용자 이름으로 검색..."
                                          @change="searchUsers"
                            ></b-form-input>
                            <hr/>
                            <ul>
                                <li v-for="result in results" :key="result.id">
                                    <div>
                                        <b-container class="container">
                                            {{result.username}}
                                            <b-btn v-if="!selected.includes(result)" class="mt-3" variant="success" @click="addToSelected(result)">
                                                에 추가
                                            </b-btn>
                                        </b-container>
                                    </div>
                                    <hr>
                                </li>
                            </ul>
                        </b-col>
                        <b-col cols="4">
                            <strong>선택된</strong>
                            <ul>
                                <li v-for="(select, index) in selected" :key="select.id">
                                    {{select.username}}
                                    <b-btn class="mt-3" variant="danger" @click="removeFromSelected(index)">
                                        제거하다
                                    </b-btn>
                                </li>
                            </ul>
                        </b-col>
                    </b-row>
                </b-container>
                <b-btn v-if="selected.length >= 1" type="submit" class="mt-3" variant="primary" style="width:100%;">
                   컴퓨터에 저장
                </b-btn>
                <b-btn v-else class="mt-3" variant="primary" block @click="hideModal">
                    끝내다
                </b-btn>
            </b-form>
        </b-modal>
    </div>
</template>

<script>
  export default {
    data() {
      return {
        groupChatName: '',
        searchString: '',
        selected: [],
        edit: false
      }
    },
    computed: {
      results(){
        let results = this.$store.state.user.userSearchResults;
        let currentUserId = this.$store.state.user.currentUser.id;
        return this.searchString ? results.filter(user => user.id !== currentUserId) : []
      },
      selectedChat() {
        let chatId = this.$store.state.chats.selectedChat;
        return this.$store.state.chats.chats.find(chat => chat.id === chatId);
      }
    },
    methods: {
      searchUsers(){
        this.$store.dispatch('user/SEARCH_USER', {username: this.searchString});
      },
      submit(evt){
        evt.preventDefault();
        if (this.edit){
          this.selected.forEach((user) => {
            this.$store.dispatch('chats/UPDATE_MEMBERS', user).then(() => {
              //console.log( res);
               
              this.$store.dispatch('chats/GET_CHATS');
            });
          });
          this.$bus.$emit("flashmessage","success","Mitglieder aktualisiert");
          this.hideModal();
        }else{
          const data = {
            isGroup: this.selected.length > 1,
            name: (this.selected.length > 1) ? this.groupChatName : this.selected[0].username,
            members: this.selected
          };
          this.$store.dispatch('chats/CREATE_CHAT', data).then(() => {
            this.$store.dispatch('chats/GET_CHATS');
            this.$bus.$emit("flashmessage","success","Chat erstellt");
            this.hideModal();
          });
        }
      },
      addToSelected(user){
        if(this.selected.filter(u => u.id == user.id).length === 0){
          this.selected.push(user);
        }
      },
      removeFromSelected(index){
        this.selected.splice(index, 1);
      },
      showModal() {
        this.$refs.newChatModal.show()
      },
      hideModal() {
        this.resetData();
        this.$refs.newChatModal.hide()
      },
      resetData(){
        this.selected = [];
        this.searchString = '';
        this.groupChatName = '';
        this.edit = false;
      },
      receiveEvent(){
        this.groupChatName = this.selectedChat.name;
        this.selected = this.selectedChat.members.filter(member => member.id !== this.$store.state.user.currentUser.id);
        this.edit = true;
        this.showModal();
      }
    },
    mounted(){
      this.$bus.$on('editMembersEvent', () => {
        this.receiveEvent();
      });
    },
    beforeDestroy(){
      this.$bus.$off('editMembersEvent');
      this.resetData();
    }
  }
</script>

<style scoped>
    ul {
        margin-left: -40px;
        list-style-type: none;
    }
    .container {
        text-align: left;
    }

    .d-block * {
        color: #000000;
    }
</style>
