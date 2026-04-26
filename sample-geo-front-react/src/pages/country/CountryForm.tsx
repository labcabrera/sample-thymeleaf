import { type Dispatch, type SetStateAction } from "react";
import { Grid, TextField } from "@mui/material";
import type { Country } from "../../lib/countries-api";

type Props = {
  create: boolean;
  formData: Country;
  setFormData: Dispatch<SetStateAction<Country>>;
};

export default function CountryForm({ create, formData, setFormData }: Props) {
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
      <Grid size={12}>
        <pre>FormData: {JSON.stringify(formData, null, 2)}</pre>
      </Grid>
    </Grid>
  );
}
