import {ChatPreview} from "./chat-preview.model";

export interface ChatPreviewPageDTO {
  chatPreviews: ChatPreview[];
  totalPages: number;
  currentPage: number;
  totalItems: number;
}
