import type { ReqPage } from "@/api/types";
import type { SearchOrderType } from "@/enums/constEnums";
import type { AppointmentStatus } from "@/enums/constEnums";
import type { AgreementSource, AgreementStatus } from "@/enums/constEnums";
import type { UserInfoInterface } from "@/api/user/types";
import type { ApartmentInterface } from "@/api/search/types";
import type { UploadFile } from "element-plus";

export interface MessageInterface {
  id: number | string;
  message: string;
  userid: number | string;
  sendTime: string;
  subject: string;
  userInfo: UserInfoInterface;
  graphVoList?:
    | {
        url: string;
        name: number;
      }[]
    | UploadFile[];
}

export interface TalkInterface {
  id: number | string;
  talk: string;
  mid: number | string;
  sendTime: string;
  userid: number | string; //收到评论的;
  senduserid: number | string;
  status: number | string;
  userInfo: UserInfoInterface;
  graphVoList?:
    | {
        url: string;
        name: number;
      }[]
    | UploadFile[];
}
export interface ContentInterface {
  id: number | string;
  talk: string;
  mid: number | string;
  message: string;
  sendTime: string;
  userid: number | string; //收到评论的;
  senduserid: number | string;
  status: number | string;
  userInfo: UserInfoInterface;
  graphVoList?:
    | {
        url: string;
        name: number;
      }[];
}

export interface MessageDetailInterface {
  id: number | string;
  message: string;
  userid: number | string;
  sendTime: string;
  subject: string;
  userInfo: UserInfoInterface;
  talkList: TalkInterface[];
  graphVoList?: {
    url: string;
    name: number;
  }[];
}
