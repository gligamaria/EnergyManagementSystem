import {Component, OnInit} from '@angular/core';
import {Token} from "../../common/token";
import {AuthService} from "../../services/auth.service";
import {PersonService} from "../../services/person.service";
import {WebSocketMessage} from "../../common/web-socket-message";
import {WebsocketService} from "../../services/websocket.service";

@Component({
  selector: 'app-client-chat',
  templateUrl: './client-chat.component.html',
  styleUrls: ['./client-chat.component.css']
})
export class ClientChatComponent{
  token: Token;
  id: string;
  start: boolean = false;

  constructor(public authService: AuthService,
              public personService: PersonService,
              private webSocketService: WebsocketService) {
    this.token = authService.getTokenFromLocalStorage();
    this.personService.getId(this.token.jwtToken).subscribe(
      data => {
        this.id = data;
        console.log("au user cu id-ul ASTA " + this.id);
      }
    );
  }

  startChat(){
    const requestChat = new WebSocketMessage(
      'Initialise chat',
      'f84c8aed-ff1a-4e6c-a08c-bbdc121f8846',
      this.id,
      'message'
    );
    this.webSocketService.sendCommonMessage(requestChat);
    this.start = true;
  }
}
