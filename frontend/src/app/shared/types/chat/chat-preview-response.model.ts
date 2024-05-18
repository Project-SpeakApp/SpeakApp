import {ChatPreviewDTO} from "./chat-preview-dto.model";

export interface ChatPreviewsResponse {
  chatPreviewDTOS: ChatPreviewDTO[];
  currentPage: number;
  totalPages: number;
}
