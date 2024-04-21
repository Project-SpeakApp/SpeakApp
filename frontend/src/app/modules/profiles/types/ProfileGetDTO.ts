type ProfileGetDTO = {
  userId: string;
  firstName: string;
  lastName: string;
  email: string;
  profilePhotoUrl: string;
  bgPhotoUrl: string;
  about: string;
  dateOfBirth: Date;
}

export default ProfileGetDTO;
