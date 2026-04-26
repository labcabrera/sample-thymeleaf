/* eslint-disable react-hooks/set-state-in-effect */
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { useAuth } from "react-oidc-context";
import { Container, IconButton } from "@mui/material";
import { createProvince, type Province } from "../../lib/provinces-api";
import SaveIcon from "@mui/icons-material/Save";
import AppBreadcrumbs from "../../components/AppBreadcrumbs";
import ProvinceForm from "./ProvinceForm";

export default function ProvinceCreate() {
  const navigate = useNavigate();
  const auth = useAuth();
  const [formData, setFormData] = useState<Province>({} as Province);
  const [isValidForm, setIsValidForm] = useState<boolean>(false);

  const onCreate = () => {
    createProvince(formData, auth).then((response) =>
      navigate(`/provinces/view/${response.id}`, {
        state: { province: response },
      }),
    );
  };

  const validate = (formData: Province) => {
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
          { label: "Provinces", href: "/provinces" },
          { label: "Creation" },
        ]}
      >
        <IconButton onClick={onCreate} color="primary" disabled={!isValidForm}>
          <SaveIcon />
        </IconButton>
      </AppBreadcrumbs>
      <ProvinceForm formData={formData} setFormData={setFormData} create />
    </Container>
  );
}
