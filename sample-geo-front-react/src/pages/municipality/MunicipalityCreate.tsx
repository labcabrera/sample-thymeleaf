/* eslint-disable react-hooks/set-state-in-effect */
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { useAuth } from "react-oidc-context";
import { Container, IconButton } from "@mui/material";
import {
  createMunicipality,
  type Municipality,
} from "../../lib/municipalities-api";
import SaveIcon from "@mui/icons-material/Save";
import AppBreadcrumbs from "../../components/AppBreadcrumbs";
import MunicipalityForm from "./MunicipalityForm";

export default function MunicipalityCreate() {
  const navigate = useNavigate();
  const auth = useAuth();
  const [formData, setFormData] = useState<Municipality>({} as Municipality);
  const [isValidForm, setIsValidForm] = useState<boolean>(false);

  const onCreate = () => {
    createMunicipality(formData, auth).then((response) =>
      navigate(`/municipalities/view/${response.id}`, {
        state: { municipality: response },
      }),
    );
  };

  const validate = (formData: Municipality) => {
    if (!formData) return false;
    if (!formData.id) return false;
    if (!formData.name) return false;
    return true;
  };

  useEffect(() => {
    setIsValidForm(validate(formData));
  }, [formData, setIsValidForm]);

  return (
    <Container>
      <AppBreadcrumbs
        items={[
          { label: "Home", href: "/" },
          { label: "Geo", href: "/geo" },
          { label: "Municipalities", href: "/municipalities" },
          { label: "Creation" },
        ]}
      >
        <IconButton onClick={onCreate} color="primary" disabled={!isValidForm}>
          <SaveIcon />
        </IconButton>
      </AppBreadcrumbs>
      <MunicipalityForm formData={formData} setFormData={setFormData} create />
    </Container>
  );
}
