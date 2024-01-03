import {PostGet} from "./post-get.model";

export interface PostGetResponse {
  posts: PostGet[];
  currentPage: number;
  pageSize: number;
  totalPages: number;
}
