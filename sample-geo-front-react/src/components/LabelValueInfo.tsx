import { Grid, Link, Stack, Typography } from "@mui/material";

type Props = {
  label: string;
  value: string | null;
  href?: string;
  gridSize?: number;
};

export default function ClearableTextField({
  label,
  value,
  href,
  gridSize = 6,
}: Props) {
  return (
    <Grid size={gridSize} sx={{ mt: 4 }}>
      <Stack direction="column">
        {href ? (
          <Link href={href}>
            <Typography variant="body1" color="primary">
              {value || "-"}
            </Typography>
          </Link>
        ) : (
          <Typography variant="body1" color="primary">
            {value || "-"}
          </Typography>
        )}
        <Typography variant="caption">{label}</Typography>
      </Stack>
    </Grid>
  );
}
