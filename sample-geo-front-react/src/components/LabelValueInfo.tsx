import { Grid, Stack, Typography } from "@mui/material";

type Props = {
  label: string;
  value: string | null;
  gridSize?: number;
};

export default function ClearableTextField({
  label,
  value,
  gridSize = 6,
}: Props) {
  return (
    <Grid size={gridSize} sx={{ mt: 4 }}>
      <Stack direction="column">
        <Typography variant="body1" color="primary">
          {value || "-"}
        </Typography>
        <Typography variant="caption">{label}</Typography>
      </Stack>
    </Grid>
  );
}
