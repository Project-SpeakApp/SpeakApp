import {PostGet} from "./post-get.model";

export interface PostGetResponse {
  result: PostGet[];
  currentPage: number;
  pageSize: number;
  totalPages: number;
}
