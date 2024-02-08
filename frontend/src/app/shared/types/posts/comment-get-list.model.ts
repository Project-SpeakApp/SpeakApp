import {CommentGetModel} from "./comment-get.model";

export interface CommentGetListModel {
  commentGetDTOS: CommentGetModel [];
  currentPage: number;
  pageSize: number;
  totalPages: number;
  totalComments: number;
}
