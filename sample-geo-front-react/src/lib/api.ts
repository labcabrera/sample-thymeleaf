import { type AuthContextProps } from "react-oidc-context";

const API_BASE = import.meta.env.VITE_API_GEO_URL ?? "http://localhost:8082";

export async function callApi(
  auth: AuthContextProps,
  input: RequestInfo,
  init: RequestInit = {},
) {
  if (!auth) throw new Error("Auth instance is required");
  if (!auth.isAuthenticated) throw new Error("Not authenticated");
  const token = auth.user?.access_token;
  if (!token) throw new Error("No access token available");

  const headers = new Headers(init.headers as HeadersInit);
  headers.set("Authorization", `Bearer ${token}`);
  init.headers = headers;

  const url =
    typeof input === "string" && input.startsWith("/")
      ? `${API_BASE}${input}`
      : input;

  const res = await fetch(url, init);
  if (!res.ok) {
    const text = await res.text().catch(() => "");
    throw new Error(
      `API request failed: ${res.status} ${res.statusText} ${text}`,
    );
  }
  return res.json();
}

export default callApi;
