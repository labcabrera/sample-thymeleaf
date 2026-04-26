/* eslint-disable react-hooks/exhaustive-deps */
/* eslint-disable react-hooks/set-state-in-effect */
import { useEffect, useState } from "react";
import { useParams, useLocation, useNavigate } from "react-router-dom";
import { useAuth } from "react-oidc-context";
import { Container, Box, CircularProgress, Stack } from "@mui/material";
import {
  fetchMunicipality,
  type Municipality,
  updateMunicipality,
} from "../../lib/municipalities-api";
import AppBreadcrumbs from "../../components/AppBreadcrumbs";
import MunicipalityForm from "./MunicipalityForm";
import SaveButton from "../../components/buttons/SaveButton";
import CancelButton from "../../components/buttons/CancelButton";

export default function MunicipalityEdit() {
  const { id } = useParams<{ id: string }>();
  const location = useLocation();
  const navigate = useNavigate();
  const auth = useAuth();
  const [municipality, setMunicipality] = useState<Municipality>();
  const [formData, setFormData] = useState<Municipality>({} as Municipality);

  const bindMunicipality = (id: string) => {
    fetchMunicipality(id!, auth).then((response) => setMunicipality(response));
  };

  const onUpdate = () => {
    updateMunicipality(formData, auth).then((response) =>
      navigate(`/municipalities/view/${response.id}`, {
        state: { municipality: response },
      }),
    );
  };

  useEffect(() => {
    if (municipality) {
      setFormData(municipality);
    }
  }, [municipality]);

  useEffect(() => {
    if (location.state?.municipality) {
      setMunicipality(location.state.municipality);
    } else if (id) {
      bindMunicipality(id);
    }
  }, [id, auth, location]);

  return (
    <Container>
      <AppBreadcrumbs
        items={[
          { label: "Home", href: "/" },
          { label: "Geo", href: "/geo" },
          { label: "Municipalities", href: "/municipalities" },
          { label: "Edit" },
        ]}
      >
        <Stack direction="row">
          <SaveButton onClick={onUpdate} />
          <CancelButton
            onClick={() =>
              navigate(`/municipalities/view/${id}`, { state: municipality })
            }
          />
        </Stack>
      </AppBreadcrumbs>
      {!formData ? (
        <CircularProgress />
      ) : (
        <Box sx={{ my: 2 }}>
          <MunicipalityForm
            formData={formData}
            setFormData={setFormData}
            create={false}
          />
        </Box>
      )}
    </Container>
  );
}
