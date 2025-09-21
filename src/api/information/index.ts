import type { PageRes, ReqPage } from "@/api/types";
import http from "@/utils/http";
import type {
  FriendsInfoInterface,
  FriendsInterface,
  InformationInterface, Mynumber,
  nwFriendsInterface
} from "@/api/information/types";
import type {
  MessageDetailInterface,
  TalkInterface
} from "@/api/message/types";
import type { MyUserInfoInterface, UserInfoInterface } from "@/api/user/types";

export function getFriendList(params: ReqPage) {
  return http.get<PageRes<FriendsInterface>>(
    `/app/information/pageItemByUserId`,
    params
  );
}
export function getDetailById(id: number | string) {
  return http.get<MyUserInfoInterface>(`/app/information/talking?userid=${id}`);
}
export function getme() {
  return http.get<MyUserInfoInterface>(`/app/information/me`);
}
export function getNoReadById(id: number | string) {
  return http.get<InformationInterface[]>(
    `/app/information/noRead?userid=${id}`
  );
}

export function getmeReadById(id: number | string) {
  return http.get<InformationInterface[]>(
    `/app/information/meRead?userid=${id}`
  );
}
export function saveOrUpdate(params: Partial<InformationInterface>) {
  return http.post(`/app/information/saveOrUpdate`, params);
}
export function saveOrUpdate3(params: Partial<InformationInterface>) {
  return http.post(`/app/information/saveOrUpdate3`, params);
}
export function getfulFriendList(params: ReqPage) {
  return http.get<PageRes<nwFriendsInterface>>(
    `/app/information/getFriend`,
    params
  );
}
export function pass(params: Partial<FriendsInfoInterface>) {
  return http.post(`/app/information/pass`, params);
}
export function reject(params: Partial<FriendsInfoInterface>) {
  return http.post(`/app/information/reject`, params);
}
export function change(params: Partial<UserInfoInterface>) {
  return http.post(`/app/change`, params);
}
