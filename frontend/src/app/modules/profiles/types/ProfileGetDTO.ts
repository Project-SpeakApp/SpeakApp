type ProfileGetDTO = {
  userId: string;
  firstName: string;
  lastName: string;
  email: string;
  profilePhotoId: string;
  bgPhotoId: string;
  about: string;
  dateOfBirth: Date;
}

export default ProfileGetDTO;
