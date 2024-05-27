import {UserGet} from "./user-get.model";

interface UserPreviewPage {
  userPreviews: UserGet[];
  currentPage: number;
  totalPages: number;
  pageSize: number;
}

export default UserPreviewPage;
