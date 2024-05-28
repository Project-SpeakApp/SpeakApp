import {MessageGetDTO} from "./message-get-DTO.model";

export interface ConversationHistoryGetDtoModel {
  listOfMessages: MessageGetDTO [];
  totalPages: number;
  currentPage: number;
}
