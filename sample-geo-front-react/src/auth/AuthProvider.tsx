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
        silentCheckSsoRedirectUri: window.location.origin + "/silent-sso.html",
      }}
      onEvent={onEvent}
    >
      {children}
    </ReactKeycloakProvider>
  );
};

export default AuthProvider;
