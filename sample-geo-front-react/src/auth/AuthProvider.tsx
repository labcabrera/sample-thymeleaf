import React from "react";
import { AuthProvider as OidcProvider } from "react-oidc-context";
import type { AuthProviderProps } from "react-oidc-context";

type Props = {
  children: React.ReactNode;
};

const AuthProvider: React.FC<Props> = ({ children }) => {
  const OIDC_AUTHORITY =
    (import.meta.env.VITE_KEYCLOAK_URL ?? "http://localhost:8090") +
    "/realms/" +
    (import.meta.env.VITE_KEYCLOAK_REALM ?? "sample");
  const OIDC_CLIENT_ID =
    import.meta.env.VITE_KEYCLOAK_CLIENT_ID ?? "sample-client";

  const config: Partial<AuthProviderProps> = {
    authority: OIDC_AUTHORITY,
    client_id: OIDC_CLIENT_ID,
    redirect_uri: window.location.origin + "/",
    response_type: "code",
    scope: "openid profile email",
    automaticSilentRenew: false,
    // silent_redirect_uri omitted to avoid iframe sandbox warnings; consider backend refresh for production
    loadUserInfo: true,
  };

  return (
    <OidcProvider {...(config as AuthProviderProps)}>{children}</OidcProvider>
  );
};

export default AuthProvider;
