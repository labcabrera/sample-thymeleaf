import Container from "@mui/material/Container";
import Typography from "@mui/material/Typography";
import Box from "@mui/material/Box";

export default function Home() {
  return (
    <Container>
      <Box sx={{ my: 4 }}>
        <Typography variant="h4" component="h1" gutterBottom>
          Sample Geo React
        </Typography>
        <Typography>
          An example of a front-end application using React and MUI for managing
          geographical entities such as countries and regions.
        </Typography>
      </Box>
    </Container>
  );
}
