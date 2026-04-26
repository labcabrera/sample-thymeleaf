import { type Dispatch, type SetStateAction } from "react";
import { Grid, TextField } from "@mui/material";
import type { Province } from "../../lib/provinces-api";

type Props = {
  create: boolean;
  formData: Province;
  setFormData: Dispatch<SetStateAction<Province>>;
};

export default function ProvinceForm({ create, formData, setFormData }: Props) {
  return (
    <Grid container spacing={1}>
      <Grid size={4}>
        <TextField
          label="Id"
          value={formData.id}
          onChange={(e) => setFormData({ ...formData, id: e.target.value })}
          disabled={!create}
          required
          fullWidth
        />
      </Grid>
      <Grid size={4}>
        <TextField
          label="Name"
          value={formData.name}
          onChange={(e) => setFormData({ ...formData, name: e.target.value })}
          required
          fullWidth
        />
      </Grid>
      <Grid size={4}>
        <TextField
          label="Country Id"
          value={formData.countryId}
          onChange={(e) =>
            setFormData({ ...formData, countryId: e.target.value })
          }
          fullWidth
        />
      </Grid>
      <Grid size={12}>
        <pre>FormData: {JSON.stringify(formData, null, 2)}</pre>
      </Grid>
    </Grid>
  );
}
