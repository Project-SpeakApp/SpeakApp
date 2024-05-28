import {UserGet} from "../profiles/user-get.model";

export interface MessageGetDTO {
  content: string;
  fromUser: UserGet;
  type: string;
  conversationId: string;
  sentAt: Date;
}
