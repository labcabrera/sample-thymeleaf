import { useEffect, useState, type Dispatch, type SetStateAction } from "react";
import { Grid } from "@mui/material";
import ProvinceSelect from "../../components/selects/ProvinceSelect";
import type { Province } from "../../lib/provinces-api";
import type { Municipality } from "../../lib/municipalities-api";

type Props = {
  create?: boolean;
  formData: Municipality;
  setFormData: Dispatch<SetStateAction<Municipality>>;
};

export default function MunicipalityForm({
  create,
  formData,
  setFormData,
}: Props) {
  const [selectedProvince, setSelectedProvince] = useState<Province | null>(
    null,
  );

  useEffect(() => {
    if (formData.provinceId) {
      setSelectedProvince({
        id: formData.provinceId,
        name: "",
        createdAt: new Date(),
        updatedAt: null,
      } as Province);
    } else {
      setSelectedProvince(null);
    }
  }, [formData.provinceId]);

  return (
    <Grid container spacing={1}>
      <Grid size={4}>
        <input type="hidden" name="_csrf" />
        <ProvinceSelect
          value={selectedProvince}
          onChange={(p) => {
            setSelectedProvince(p);
            setFormData({ ...formData, provinceId: p?.id ?? "" });
          }}
          size="small"
          sx={{ width: "100%" }}
          disabled={!create}
        />
      </Grid>
      <Grid size={4}>
        <Grid>
          <label>Id</label>
        </Grid>
        <input
          value={formData.id ?? ""}
          onChange={(e) => setFormData({ ...formData, id: e.target.value })}
          disabled={!create}
        />
      </Grid>
      <Grid size={4}>
        <Grid>
          <label>Name</label>
        </Grid>
        <input
          value={formData.name ?? ""}
          onChange={(e) => setFormData({ ...formData, name: e.target.value })}
        />
      </Grid>
    </Grid>
  );
}
