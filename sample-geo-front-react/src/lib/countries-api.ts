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

import type { AuthContextProps } from "react-oidc-context";
import callApi from "./api";

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
