import type { UserInfoInterface } from "@/api/user/types";
import type { UploadFile } from "element-plus";

export interface FriendsInterface {
  id: number | string;
  nickname: string;
  avatarUrl: string;
  count: string;
}
export interface InformationInterface {
  id: number | string;
  fromId: number | string;
  toId: number | string;
  text: string;
}
export interface nwFriendsInterface {
  id: number | string;
  nickname: string;
  avatarUrl: string;
}
export interface FriendsInfoInterface {
  firstId: number | string;
  secondId: number | string;
  status: number | string;
}
export interface Mynumber {
  Id: number | string;
}
