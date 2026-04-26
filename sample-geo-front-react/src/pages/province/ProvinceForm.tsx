/* eslint-disable react-hooks/set-state-in-effect */
import { useEffect, useState, type Dispatch, type SetStateAction } from "react";
import { Grid, TextField } from "@mui/material";
import { useAuth } from "react-oidc-context";
import CountrySelect from "../../components/selects/CountrySelect";
import type { Country } from "../../lib/countries-api";
import { fetchCountry } from "../../lib/countries-api";
import type { Province } from "../../lib/provinces-api";

type Props = {
  create: boolean;
  formData: Province;
  setFormData: Dispatch<SetStateAction<Province>>;
};

export default function ProvinceForm({ create, formData, setFormData }: Props) {
  const auth = useAuth();
  const [selectedCountry, setSelectedCountry] = useState<Country | null>(null);

  useEffect(() => {
    if (formData.countryId) {
      fetchCountry(formData.countryId, auth)
        .then((c) => setSelectedCountry(c))
        .catch(() => setSelectedCountry(null));
    } else {
      setSelectedCountry(null);
    }
  }, [formData.countryId, auth]);

  return (
    <Grid container spacing={1}>
      <Grid size={4}>
        <TextField
          label="Id"
          value={formData.id ?? ""}
          onChange={(e) => setFormData({ ...formData, id: e.target.value })}
          disabled={!create}
          required
          fullWidth
        />
      </Grid>
      <Grid size={4}>
        <TextField
          label="Name"
          value={formData.name ?? ""}
          onChange={(e) => setFormData({ ...formData, name: e.target.value })}
          required
          fullWidth
        />
      </Grid>
      <Grid size={4}>
        <CountrySelect
          value={selectedCountry}
          onChange={(c) => {
            setSelectedCountry(c);
            setFormData({ ...formData, countryId: c?.id ?? "" });
          }}
        />
      </Grid>
      <Grid size={12}>
        <pre>FormData: {JSON.stringify(formData, null, 2)}</pre>
      </Grid>
    </Grid>
  );
}
