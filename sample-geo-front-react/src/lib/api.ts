import type { KeycloakInstance } from "keycloak-js";

const API_BASE = import.meta.env.VITE_API_GEO_URL ?? "http://localhost:8080";

export async function callApi(
  keycloak: KeycloakInstance | undefined,
  input: RequestInfo,
  init: RequestInit = {},
) {
  if (!keycloak) throw new Error("Keycloak instance is required");

  // try to refresh token if expiring in the next 5 seconds
  try {
    await keycloak.updateToken(5);
  } catch (e) {
    // updateToken throws if it cannot refresh; we'll still try with existing token
  }

  const token = keycloak.token;
  if (!token) throw new Error("No access token available");

  const headers = new Headers(init.headers as HeadersInit);
  headers.set("Authorization", `Bearer ${token}`);
  init.headers = headers;

  // prefix relative URLs with API_BASE
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
