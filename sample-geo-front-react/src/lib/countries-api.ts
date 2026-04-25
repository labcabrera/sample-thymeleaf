// import { useAuth, type AuthContextProps } from "react-oidc-context";
import callApi from "./api";
import { type AuthContextProps } from "react-oidc-context";

export interface Country {
  id: string;
  name: string;
}

export interface Pagination {
  page: number;
  size: number;
  totalElements: number;
  totalPages: number;
}

export interface Page<T> {
  content: T[];
  pagination: Pagination;
}

export const fetchCountries = async (
  rsql: string,
  size: number,
  page: number,
  sort: string,
  auth: AuthContextProps,
): Promise<Page<Country>> => {
  const data = await callApi(
    auth,
    `/countries?q=${rsql}&page=${page}&size=${size}&sort=${sort}`,
  );
  return data as Page<Country>;
};

export const fetchCountry = async (
  countryId: string,
  auth: AuthContextProps,
): Promise<Country> => {
  const data = await callApi(auth, `/countries${countryId}`);
  return data as Country;
};
