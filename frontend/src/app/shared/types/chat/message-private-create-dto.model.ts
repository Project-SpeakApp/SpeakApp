export interface MessagePrivateCreateDTO {
  fromUserId: string;
  toUserId: string;
  content: string;
  type: string;
  conversationId: string;
}
