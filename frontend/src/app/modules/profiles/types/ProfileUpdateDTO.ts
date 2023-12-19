interface ProfileUpdateDTO {
  firstName: string;
  lastName: string;
  about: string | null | undefined;
  dateOfBirth: Date;
}

export default ProfileUpdateDTO;
