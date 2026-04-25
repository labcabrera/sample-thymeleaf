import Keycloak from "keycloak-js";

const url =
  import.meta.env.VITE_KEYCLOAK_URL || "https://keycloak.example.com/auth";
const realm = import.meta.env.VITE_KEYCLOAK_REALM || "myrealm";
const clientId = import.meta.env.VITE_KEYCLOAK_CLIENT_ID || "my-client";

const keycloak = new Keycloak({
  url,
  realm,
  clientId,
});

export default keycloak;
