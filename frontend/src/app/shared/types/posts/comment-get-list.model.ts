import {CommentGetModel} from "./comment-get.model";

export interface CommentGetListModel {
  comments: CommentGetModel [];
  currentPage: number;

  totalNumberOfComments: number;
}
