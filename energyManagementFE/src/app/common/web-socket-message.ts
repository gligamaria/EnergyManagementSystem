export class WebSocketMessage {
  message: string;
  receiver: string;
  sender: string;
  type: string;

  constructor(message: string, receiver: string, sender: string, type: string) {
    this.message = message;
    this.receiver = receiver;
    this.sender = sender;
    this.type = type;
  }
}
