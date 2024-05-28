import {UserGet} from "../profiles/user-get.model";
import {MessageGetDTO} from "./message-get-DTO.model";
import {ConversationGetDTO} from "./conversation-get-dto.model";


export interface ChatPreviewDTO {
  chatMembers: UserGet[];
  conversationGetDTO: ConversationGetDTO;
  lastMessage: MessageGetDTO;
}
