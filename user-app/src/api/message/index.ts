import type { PageRes, ReqPage } from "@/api/types";
import http from "@/utils/http";
import type {
  AppointmentItemInterface, RegionInterface,
  RoomDetailInterface,
  RoomInterface,
  RoomListQueryInterface
} from "@/api/search/types";
import type {
  ContentInterface,
  MessageDetailInterface,
  MessageInterface,
  TalkInterface
} from "@/api/message/types";
import router from "@/router";
import path from "path";
//留的这个是根据用户id获取他发过的帖子
export function getMessageListByUserId(
  params: ReqPage & { id: number | string }
) {
  return http.get<PageRes<MessageInterface>>(
    `/app/message/pageItemByUserId`,
    params
  );
}

export function getMessageList(params: ReqPage) {
  return http.get<PageRes<MessageInterface>>(`/app/message/pageItem`, params);
}
export function getMessageList2(params: ReqPage & { keyword?: string }) {
  return http.get<PageRes<MessageInterface>>(`/app/message/pageItem2`, params);
}
export function getNotReadList(params: ReqPage) {
  return http.get<PageRes<ContentInterface>>(`/app/message/getRead`, params);
}

export function getMessageDetailById(id: number | string) {
  return http.get<MessageDetailInterface>(
    `/app/message/getDetailById?id=${id}`
  );
}

export function saveOrUpdatetiezi(params: Partial<MessageInterface>) {
  return http.post(`/app/message/saveOrUpdate`, params);
}
export function saveOrUpdateWorry(params: Partial<MessageInterface>) {
  return http.post(`/app/message/worry`, params);
}
export function saveOrUpdatepinglun2(params: Partial<TalkInterface>) {
  return http.post(`/app/message/saveContent2`, params);
}
export function saveOrUpdatepinglun(params: Partial<TalkInterface>) {
  return http.post(`/app/message/saveContent`, params);
}
export function getSearch(search: string) {
  return http.get<PageRes<MessageInterface>>(
    `/app/message/search?id=${search}`
  );
}
export function getMyNotReadNumber() {
  return http.post<AppointmentItemInterface[]>(`/app/message/getNotRead`);
}

export function getMyFriendNumber() {
  return http.post<number>(`/app/information/getNotRead`);
}
