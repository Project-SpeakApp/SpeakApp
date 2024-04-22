import {CommentGetModel} from "./comment-get.model";

export interface CommentGetListModel {
  commentGetDTOS: CommentGetModel [];
  firstComment: number;
  lastComment: number;
  totalComments: number;
}
