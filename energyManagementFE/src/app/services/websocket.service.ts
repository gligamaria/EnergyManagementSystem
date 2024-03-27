import { Injectable } from '@angular/core';
import {Observable, Subject} from 'rxjs';
import * as SockJS from 'sockjs-client';
import * as Stomp from 'stompjs';
import {WebSocketMessage} from "../common/web-socket-message";
import {AuthService} from "./auth.service";
import {PersonService} from "./person.service";
import {Token} from "../common/token";

@Injectable({
  providedIn: 'root',
})

export class WebsocketService {

  token: Token;
  role: string;
  id: string;

  constructor(private authService: AuthService,
              private personService: PersonService) {
    this.initializeWebSocketConnection();
    this.token = this.authService.getTokenFromLocalStorage();
    this.handleConnectionByRole();
    this.Obs=new Subject();
  }
  private stompClient:any;
  public Obs:Subject<any>;
  public notification = "";
  private commonWebSocketConnected = false; // Add a flag to track connection status
  public usersChatting : string[] = [];
  private usersSubject: Subject<string> = new Subject<string>();

  // private messages: WebSocketMessage[] = []; // Array to store messages
  // private messageSubject: Subject<WebSocketMessage> = new Subject<WebSocketMessage>();

  private messagesMap: { [userId: string]: WebSocketMessage[] } = {};
  private messageSubjectMap: { [userId: string]: Subject<WebSocketMessage> } = {};


  handleConnectionByRole() {
    this.personService.getRole(this.token.jwtToken).subscribe(
      data => {
        this.role = data;

        this.personService.getId(this.token.jwtToken).subscribe(
          data => {
            this.id = data;
            console.log(this.id);
            if(this.role == '1'){
              this.initializeCommonWebSocketConnection()
            }
            else {
              // subscribe to its own private websocket
              this.messagesMap[this.id] = [];
              this.messageSubjectMap[this.id] = new Subject<WebSocketMessage>();
              this.initializePrivateWebSocketConnection(this.id);
            }
          })
      }
    );
  }

  // getMessages(): WebSocketMessage[] {
  //   return this.messages;
  // }
  //
  // getMessageSubject(): Observable<WebSocketMessage> {
  //   return this.messageSubject.asObservable();
  // }

  getMessages(userId: string): WebSocketMessage[] {
    if (!this.messagesMap[userId]) {
      this.messagesMap[userId] = [];
    }
    return this.messagesMap[userId];
  }

  getMessageSubject(userId: string): Observable<WebSocketMessage> {
    if (!this.messageSubjectMap[userId]) {
      this.messageSubjectMap[userId] = new Subject<WebSocketMessage>();
    }
    return this.messageSubjectMap[userId].asObservable();
  }


  getUsers(): string[] {
    return this.usersChatting;
  }

  getUsersSubject(): Observable<string> {
    return this.usersSubject.asObservable();
  }

  initializeWebSocketConnection() {

    if (this.commonWebSocketConnected) {
      return; // Avoid duplicate subscriptions
    }

    const serverUrl = 'http://localhost:8082/ws';
    const ws = new SockJS(serverUrl);
    this.stompClient = Stomp.over(ws);
    this.stompClient.connect({}, (frame: any) => {
      console.log("Connected:"+ frame);
      this.stompClient.subscribe('/topic/greetings', (message: Stomp.Message) => {
        this.notification = message.body;
        console.log(message);
        this.Obs.next({"active":true});
      });
    });
  }

  sendMessage(message1: any) {
    const message:any={'message':message1};
    console.log("Sending message: \n", message)
    this.stompClient.send('/app/hello' ,  {"content-type":"application/json"}, JSON.stringify(message));
  }

  initializePrivateWebSocketConnection(userId: string) {
    // if (this.commonWebSocketConnected) {
    //   return; // Avoid duplicate subscriptions
    // }

    const serverUrl = 'http://localhost:8084/ws';
    const ws = new SockJS(serverUrl);
    this.stompClient = Stomp.over(ws);

    this.stompClient.connect({}, (frame: any) => {
      console.log('Connected:' + frame);
      this.stompClient.subscribe(`/user/${userId}/private`, (message: Stomp.Message) => {
        console.log(message);
        const receivedMessage: WebSocketMessage = JSON.parse(message.body);
        console.log(receivedMessage);
        // this.messageSubject.next(receivedMessage); // Emit the received message to observers
        this.messageSubjectMap[userId].next(receivedMessage); // Emit the received message to observers

      });
    });

    this.commonWebSocketConnected = true;
  }

  initializeCommonWebSocketConnection() {
    const serverUrl = 'http://localhost:8084/ws';
    const ws = new SockJS(serverUrl);
    this.stompClient = Stomp.over(ws);
    this.stompClient.connect({}, (frame: any) => {
      console.log('Connected:' + frame);
      this.stompClient.subscribe(`/common`, (message: Stomp.Message) => {
        console.log(message);
        const receivedMessage: WebSocketMessage = JSON.parse(message.body);
        const sender = receivedMessage.sender;
        this.usersChatting.push(sender);
        console.log("useri chatting: " + this.usersChatting);
        this.usersSubject.next(sender);
        this.initializePrivateWebSocketConnection(sender);
      });
    });
  }

  sendPrivateMessage(message1: any) {
    console.log("Sending message: \n", message1.message)
    this.stompClient.send('/app/private-message' ,  {}, JSON.stringify(message1));
  }

  sendCommonMessage(message1: WebSocketMessage) {
    console.log("Sending message: \n", message1.message)
    this.stompClient.send('/app/message' ,  {}, JSON.stringify(message1));
  }
}
