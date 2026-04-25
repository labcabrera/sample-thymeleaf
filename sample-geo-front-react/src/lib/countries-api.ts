import callApi, { type Page } from "./api";
import { type AuthContextProps } from "react-oidc-context";

export interface Country {
  id: string;
  name: string;
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
  const data = await callApi(auth, `/countries/${countryId}`);
  return data as Country;
};

export const createCountry = async (
  country: Country,
  auth: AuthContextProps,
): Promise<Country> => {
  const data = await callApi(auth, `/countries`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(country),
  });
  return data as Country;
};

export const updateCountry = async (
  country: Country,
  auth: AuthContextProps,
): Promise<Country> => {
  const { id, ...rest } = country;
  const data = await callApi(auth, `/countries/${id}`, {
    method: "PATCH",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(rest),
  });
  return data as Country;
};

export const deleteCountry = async (
  countryId: string,
  auth: AuthContextProps,
): Promise<void> => {
  await callApi(auth, `/countries/${countryId}`, { method: "DELETE" });
};
