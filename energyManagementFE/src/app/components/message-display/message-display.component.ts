import {Component, Input, OnInit} from '@angular/core';
import {WebSocketMessage} from "../../common/web-socket-message";
import {WebsocketService} from "../../services/websocket.service";
import {AuthService} from "../../services/auth.service";
import {Token} from "../../common/token";
import {PersonService} from "../../services/person.service";

@Component({
  selector: 'app-message-display',
  templateUrl: './message-display.component.html',
  styleUrls: ['./message-display.component.css']
})

export class MessageDisplayComponent implements OnInit {

  messages: WebSocketMessage[] = [];
  token: Token;
  id: string;
  role: string;
  newMessage: string = '';
  open: boolean = true;
  status: string = '';
  @Input() otherUser: string;
  messageSent: boolean = false;
  chatInitiated: boolean = false;

  constructor(private webSocketService: WebsocketService,
              private authService: AuthService,
              private personService: PersonService) {
    this.token = authService.getTokenFromLocalStorage();
  }

  ngOnInit() {

    console.log("Sunt la user-ul " + this.otherUser);
    this.personService.getId(this.token.jwtToken).subscribe(
      data => {
        this.id = data;
      }
    );
    this.personService.getRole(this.token.jwtToken).subscribe(
      data => {
        this.role = data;
      }
    );

    this.messages = this.webSocketService.getMessages(this.otherUser);
    this.webSocketService.getMessageSubject(this.otherUser).subscribe((message) => {
      if(message.type == 'message client'){
        this.chatInitiated = true;
        console.log(this.messages);
        this.messages.push(message);
        if(this.id != message.sender && this.open)
          this.status = 'seen';
      }
      else if(message.type == 'message admin'){
        this.messages.push(message);
        if(this.id != message.sender && this.open)
          this.status = 'seen';
      }
      else if(message.type == 'status' &&
        !(message.message == 'typing' &&message.sender == this.id)){
        this.status = message.message;
      }
    });
  }

  sendMessage(){
    let messageToSend;
    this.messageSent = true;
    if (this.newMessage.trim() !== '') {
      if(this.role == '0'){
        messageToSend = new WebSocketMessage(
          this.newMessage,
          '00000000-0000-0000-0000-000000000000',
          this.id,
          'message client'
        );
      }
      else {
        messageToSend = new WebSocketMessage(
          this.newMessage,
          this.otherUser,
          this.id,
          'message admin'
        );
      }
      this.webSocketService.sendPrivateMessage(messageToSend);
      this.newMessage = '';
      this.status = 'sent';
    }
  }

  onTyping(){
    if(this.role == '0'){
      const messageToSend = new WebSocketMessage(
        'typing',
        '00000000-0000-0000-0000-000000000000',
        this.id,
        'status'
      );
      this.webSocketService.sendPrivateMessage(messageToSend);
    }
    else {
      const messageToSend = new WebSocketMessage(
        'typing',
        this.otherUser,
        this.id,
        'status'
      );
      this.webSocketService.sendPrivateMessage(messageToSend);
    }
  }

  openChat(){
    this.open = true;
    const messageToSend = new WebSocketMessage(
      'seen',
      '00000000-0000-0000-0000-000000000000',
      this.id,
      'status'
    );
    this.webSocketService.sendPrivateMessage(messageToSend);
  }

  closeChat(){
    this.open = false;
  }

  handleDivClick(){
    if(this.role == '0'){
      const messageToSend = new WebSocketMessage(
        'seen',
        '00000000-0000-0000-0000-000000000000',
        this.id,
        'status'
      );
      this.webSocketService.sendPrivateMessage(messageToSend);
    }
   else {
      const messageToSend = new WebSocketMessage(
        'seen',
        this.otherUser,
        this.id,
        'status'
      );
      this.webSocketService.sendPrivateMessage(messageToSend);
    }
  }

}
