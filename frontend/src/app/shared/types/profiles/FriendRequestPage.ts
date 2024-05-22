import {UserGet} from "./user-get.model";

interface FriendRequestPage {
  friendRequests: FriendRequestGet[];
  currentPage: number;
  pageSize: number;
  totalPages: number;
}

export default FriendRequestPage;

export interface FriendRequestGet {
  id: string;
  requester: UserGet;
}
