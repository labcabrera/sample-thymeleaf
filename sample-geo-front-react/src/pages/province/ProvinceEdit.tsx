/* eslint-disable react-hooks/exhaustive-deps */
/* eslint-disable react-hooks/set-state-in-effect */
import { useEffect, useState } from "react";
import { useParams, useLocation, useNavigate } from "react-router-dom";
import { useAuth } from "react-oidc-context";
import { Container, Box, CircularProgress, Stack } from "@mui/material";
import { fetchProvince, type Province } from "../../lib/provinces-api";
import AppBreadcrumbs from "../../components/AppBreadcrumbs";
import ProvinceForm from "./ProvinceForm";
import SaveButton from "../../components/buttons/SaveButton";
import CancelButton from "../../components/buttons/CancelButton";
import { updateProvince } from "../../lib/provinces-api";

export default function ProvinceEdit() {
  const { id } = useParams<{ id: string }>();
  const location = useLocation();
  const navigate = useNavigate();
  const auth = useAuth();
  const [province, setProvince] = useState<Province>();
  const [formData, setFormData] = useState<Province>({} as Province);

  const bindProvince = (id: string) => {
    fetchProvince(id!, auth).then((response) => setProvince(response));
  };

  const onUpdate = () => {
    updateProvince(formData, auth).then((response) =>
      navigate(`/provinces/view/${response.id}`, {
        state: { province: response },
      }),
    );
  };

  useEffect(() => {
    if (province) {
      setFormData(province);
    }
  }, [province]);

  useEffect(() => {
    if (location.state?.province) {
      setProvince(location.state.province);
    } else if (id) {
      bindProvince(id);
    }
  }, [id, auth, location]);

  return (
    <Container>
      <AppBreadcrumbs
        items={[
          { label: "Home", href: "/" },
          { label: "Geo", href: "/geo" },
          { label: "Provinces", href: "/provinces" },
          { label: "Edit" },
        ]}
      >
        <Stack direction="row">
          <SaveButton onClick={onUpdate} />
          <CancelButton
            onClick={() =>
              navigate(`/provinces/view/${id}`, { state: province })
            }
          />
        </Stack>
      </AppBreadcrumbs>
      {!formData ? (
        <CircularProgress />
      ) : (
        <Box sx={{ my: 2 }}>
          <ProvinceForm
            formData={formData}
            setFormData={setFormData}
            create={false}
          />
        </Box>
      )}
    </Container>
  );
}
