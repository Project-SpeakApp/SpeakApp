import {UserGet} from "../profiles/user-get.model";
import {ReactionsGet} from "./reactions-get.model";
import {ReactionType} from "./ReactionType.enum";

export interface PostGet {
  postId: string;
  content: string;
  author: UserGet;
  createdAt: Date;
  modifiedAt: Date | null;
  reactions: ReactionsGet;
  currentUserReaction: ReactionType | null;
  totalNumberOfComments: number;
  mediaId: string | null;
}
