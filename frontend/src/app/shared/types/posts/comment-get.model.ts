import {ReactionsGet} from "./reactions-get.model";
import {UserGet} from "../profiles/user-get.model";
import {ReactionType} from "./ReactionType.enum";

export interface CommentGetModel {
  commentId: string;
  content: string;
  author: UserGet;
  reactionsGetDTO: ReactionsGet;
  createdAt: Date;
  modifiedAt: Date | null;
  currentUserReactionType: ReactionType | null;
}
