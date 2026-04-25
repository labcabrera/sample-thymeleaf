import React from "react";
import { ReactKeycloakProvider } from "@react-keycloak/web";
import keycloak from "./keycloak";

type Props = {
  children: React.ReactNode;
};

const AuthProvider: React.FC<Props> = ({ children }) => {
  const onEvent = (_event: any, _error: any) => {
    // placeholder for event handling during development
  };

  return (
    <ReactKeycloakProvider
      authClient={keycloak}
      initOptions={{
        onLoad: "check-sso",
        // Note: silentCheckSsoRedirectUri is omitted to avoid creating a sandboxed iframe
        // that triggers the browser warning about allow-scripts + allow-same-origin.
        // If you need silent SSO, consider a backend token refresh flow instead.
      }}
      onEvent={onEvent}
    >
      {children}
    </ReactKeycloakProvider>
  );
};

export default AuthProvider;
