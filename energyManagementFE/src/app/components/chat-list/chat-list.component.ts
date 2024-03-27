import {Component, OnInit} from '@angular/core';
import {WebsocketService} from "../../services/websocket.service";

@Component({
  selector: 'app-chat-list',
  templateUrl: './chat-list.component.html',
  styleUrls: ['./chat-list.component.css']
})
export class ChatListComponent implements OnInit {

  public users: string[] = [];

  constructor(private webSocketService: WebsocketService) {
  }

  ngOnInit() {
    this.users = this.webSocketService.getUsers();
    this.webSocketService.getUsersSubject().subscribe((userId) => {
      if(!this.users.includes(userId)){
        this.users.push(userId);
      }
    });
  }
}
