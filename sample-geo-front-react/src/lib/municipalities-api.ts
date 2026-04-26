import callApi, { type Page } from "./api";
import { type AuthContextProps } from "react-oidc-context";

export interface Municipality {
  id: string;
  name: string;
  provinceId: string;
  createdAt: Date;
  updatedAt: Date | null;
}

export const fetchMunicipalities = async (
  rsql: string,
  size: number,
  page: number,
  sort: string,
  auth: AuthContextProps,
): Promise<Page<Municipality>> => {
  const data = await callApi(
    auth,
    `/municipalities?q=${rsql}&page=${page}&size=${size}&sort=${sort}`,
  );
  return data as Page<Municipality>;
};

export const fetchMunicipality = async (
  municipalityId: string,
  auth: AuthContextProps,
): Promise<Municipality> => {
  const data = await callApi(auth, `/municipalities/${municipalityId}`);
  return data as Municipality;
};

export const createMunicipality = async (
  municipality: Municipality,
  auth: AuthContextProps,
): Promise<Municipality> => {
  const data = await callApi(auth, `/municipalities`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(municipality),
  });
  return data as Municipality;
};

export const updateMunicipality = async (
  municipality: Municipality,
  auth: AuthContextProps,
): Promise<Municipality> => {
  const { id, ...rest } = municipality;
  const data = await callApi(auth, `/municipalities/${id}`, {
    method: "PATCH",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(rest),
  });
  return data as Municipality;
};

export const deleteMunicipality = async (
  municipalityId: string,
  auth: AuthContextProps,
): Promise<void> => {
  await callApi(auth, `/municipalities/${municipalityId}`, {
    method: "DELETE",
  });
};
