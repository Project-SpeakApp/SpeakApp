type ProfileGetDTO = {
  userId: string;
  firstName: string;
  lastName: string;
  email: string;
  profilePhotoId: string;
  bgPhotoId: string;
  about: string;
  dateOfBirth: Date;
  friendStatus: FriendStatus | null;
}

export default ProfileGetDTO;

export enum FriendStatus {
  FRIEND = 'FRIEND',
  REQUEST_SENT = 'REQUEST SENT',
  REQUEST_TO_ACCEPT = 'REQUEST TO ACCEPT',
}
