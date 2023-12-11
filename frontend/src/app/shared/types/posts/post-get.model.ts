import {UserGet} from "../profiles/user-get.model";
import {ReactionsGet} from "./reactions-get.model";
import {ReactionType} from "./ReactionType.enum";

export interface PostGet {
  postId: string;
  content: string;
  author: UserGet;
  createdAt: Date;
  reactions: ReactionsGet;
  currentUserReaction: ReactionType;
}
