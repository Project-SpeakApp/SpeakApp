import {ChatPreviewDTO} from "./chat-preview-dto.model";

export interface ChatPreviewsResponse {
  chatPreviewDTOS: ChatPreviewDTO[];
  currentPage: number;
  pageSize: number;
  totalPages: number;
}
