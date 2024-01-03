import {ReactionsGet} from "./reactions-get.model";
import {UserGet} from "../profiles/user-get.model";

export interface CommentGetModel {
  commentId: string;
  content: string;
  createdAt: Date;
  modifiedAt: Date | null;
  author: UserGet;
  reactions: ReactionsGet;
}
