import callApi, { type Page } from "./api";
import { type AuthContextProps } from "react-oidc-context";

export interface Province {
  id: string;
  name: string;
  countryId: string;
  createdAt: string;
  updatedAt: string | null;
}

export const fetchProvinces = async (
  rsql: string,
  size: number,
  page: number,
  sort: string,
  auth: AuthContextProps,
): Promise<Page<Province>> => {
  const data = await callApi(
    auth,
    `/provinces?q=${rsql}&page=${page}&size=${size}&sort=${sort}`,
  );
  return data as Page<Province>;
};

export const fetchProvince = async (
  provinceId: string,
  auth: AuthContextProps,
): Promise<Province> => {
  const data = await callApi(auth, `/provinces/${provinceId}`);
  return data as Province;
};

export const createProvince = async (
  province: Province,
  auth: AuthContextProps,
): Promise<Province> => {
  const data = await callApi(auth, `/provinces`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(province),
  });
  return data as Province;
};

export const updateProvince = async (
  province: Province,
  auth: AuthContextProps,
): Promise<Province> => {
  const { id, ...rest } = province;
  const data = await callApi(auth, `/provinces/${id}`, {
    method: "PATCH",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(rest),
  });
  return data as Province;
};

export const deleteProvince = async (
  provinceId: string,
  auth: AuthContextProps,
): Promise<void> => {
  await callApi(auth, `/provinces/${provinceId}`, { method: "DELETE" });
};
