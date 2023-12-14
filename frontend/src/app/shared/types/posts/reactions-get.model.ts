import {ReactionType} from "./ReactionType.enum";

export interface ReactionsGet {
  sumOfReactions: number;
  sumOfReactionsByType: Map<ReactionType, number>;
}
